host: 106.14.220.63

port:8000

## 用户注册

POST /api/user/sign

- body:

```
{
  "username" : "",
  "email" : "",
  "password" : "",
  "invitation" : ""
}
```

返回：token

## 用户登录

POST /api/user/login

- body:

```
{
  "username" : "",
  "password" : ""
}
```
返回：token

## 用户视频信息

GET /api/user/info

- header:

Authorization： Bearer {token}

返回：

```
{
    "status": 200,
    "result": [
        {
            "id": 1,
            "fileSize": 235,
            "status": 2,
            "uploadTm": "2019-08-23T07:04:33.000+0000",
            "finishTm": null
        },
        {
            "id": 2,
            "fileSize": 235,
            "status": 2,
            "uploadTm": "2019-08-23T07:04:53.000+0000",
            "finishTm": null
        }
    ]
}
```

## 上传视频

POST /api/video/upload

- header:

Authorization： Bearer {token}

Content-Type: multipart/form-data

type: 

- form-data:

file:

返回：视频id

## 下载字幕文件
GET /api/video/download/{fileId}

- header:

Authorization： Bearer {token}

## 查看排队情况

POST /api/video/rank

- header

Authorization： Bearer {token}

- form-data:

fileId:





