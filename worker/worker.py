import logging
import os
from prometheus_client import start_http_server, Gauge, Summary
from redis import Redis
import requests
import time

DEBUG = os.environ.get("DEBUG", "").lower().startswith("y")

log = logging.getLogger(__name__)
if DEBUG:
    logging.basicConfig(level=logging.DEBUG)
else:
    logging.basicConfig(level=logging.INFO)
    logging.getLogger("requests").setLevel(logging.WARNING)

# Create Redis client
redis = Redis("redis")
# Create a metric to track time spent and requests made.
REQUEST_TIME_RNG = Summary('rng_request_processing_seconds', 'Time spent processing RNG request')
REQUEST_TIME_HASHER = Summary('hasher_request_processing_seconds', 'Time spent processing Hasher request')
GAUGE_CALLS_RNG = Gauge('rng_requests_number', 'Number of requests calls for RNG')
GAUGE_CALLS_HASHER = Gauge('hasher_requests_number', 'Number of requests calls for Hasher')
GAUGE_REDIS_SAVE = Gauge('redis_requests_number', 'Number of redis requests to save hashes')


@REQUEST_TIME_RNG.time() # Decorate function with metric.
def get_random_bytes():
    r = requests.get("http://rng:8080/")
    return r.content


@REQUEST_TIME_HASHER.time() # Decorate function with metric.
def hash_bytes(data):
    r = requests.post("http://hasher:8080/",
                      data=data,
                      headers={"Content-Type": "application/octet-stream"})
    hex_hash = r.text
    return hex_hash


def work_loop(interval=1):
    deadline = 0
    loops_done = 0
    while True:
        if time.time() > deadline:
            log.info("{} units of work done, updating hash counter".format(loops_done))
            redis.incrby("hashes", loops_done)
            loops_done = 0
            deadline = time.time() + interval
        work_once()
        loops_done += 1


def work_once():
    log.debug("Doing one unit of work")
    time.sleep(0.1)
    random_bytes = get_random_bytes()
    GAUGE_CALLS_RNG.inc()
    hex_hash = hash_bytes(random_bytes)
    GAUGE_CALLS_HASHER.inc()
    #if not hex_hash.startswith('0'):
    #    log.debug("No hash found")
    #    return
    log.info("Hash found: {}...".format(hex_hash[:8]))
    created = redis.hset("vault", hex_hash, random_bytes)
    GAUGE_REDIS_SAVE.inc()
    if not created:
        log.info("We already had that hash")


if __name__ == "__main__":
    # Start up the server to expose the metrics.
    start_http_server(8080)
    GAUGE_CALLS_RNG.set(0)
    GAUGE_CALLS_HASHER.set(0)
    GAUGE_REDIS_SAVE.set(0)
    while True:
        try:
            work_loop()
        except:
            log.exception("In work loop:")
            log.error("Waiting 10s and restarting.")
            time.sleep(10)
