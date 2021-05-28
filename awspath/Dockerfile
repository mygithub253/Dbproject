FROM python:3.9
COPY ./abc.py /deploy/
COPY ./requirements.txt /deploy/
COPY ./dbmodel1.pkl /deploy/
WORKDIR /deploy/
RUN pip install -r requirements.txt
RUN pip install numpy==1.19.3
RUN pip install scikit-learn==0.24.1
EXPOSE 80
ENTRYPOINT ["python", "abc.py"]
