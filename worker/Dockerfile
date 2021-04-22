FROM python:alpine
RUN pip install redis
RUN pip install prometheus-client
RUN pip install requests
COPY worker.py /
CMD ["python", "worker.py"]
EXPOSE 80