MyCamera = Class(NObject, {
    picUrl: null,
    canUploadTag: false,
    initialize: function() {
        this.param = {
            uploadUrl: "",
            uploadImageKey: "file",
            uploadParam: {},
            uploadCantExe: function() {
                //alert("不具备上传的条件")
            },
            uploadSuccess: function() {},
            uploadFailed: function() {},
            onUploadProgress: function() {},
            onGetPictureSuccess: function() {},
            onGetPictureFail: function() {},
            quality: 80,
            allowEdit: false,
            destinationType: Camera.DestinationType.FILE_URI,
            targetWidth: 400,
            targetHeight: 320,
            sourceType: Camera.PictureSourceType.SAVEDPHOTOALBUM,
            encodingType: Camera.EncodingType.JPEG,
            correctOrientation: true
        };
        if (arguments[0]) {
            this.param = Util.extend(this.param, arguments[0])
        }
    },
    capture: function() {
        //alert("capture");
        navigator.camera.getPicture(Util.proxy(this._onGetPictureSuccess, this), Util.proxy(this._onGetPictureFail, this), {
            quality: this.param.quality,
            allowEdit: this.param.allowEdit,
            destinationType: this.param.destinationType,
            targetWidth: this.param.targetWidth,
            targetHeight: this.param.targetHeight,
            correctOrientation: this.param.correctOrientation
        })
    },
    getPicture: function() {
        //alert("getPicture");
        navigator.camera.getPicture(Util.proxy(this._onGetPictureSuccess, this), Util.proxy(this._onGetPictureFail, this), {
            sourceType: Camera.PictureSourceType.PHOTOLIBRARY,
            encodingType: this.param.encodingType,
            quality: this.param.quality,
            allowEdit: this.param.allowEdit,
            destinationType: Camera.DestinationType.FILE_URI,
            targetWidth: this.param.targetWidth,
            targetHeight: this.param.targetHeight,
            correctOrientation: this.param.correctOrientation
        })
    },
    _onGetPictureSuccess: function(image) {
        //alert("_onGetPictureSuccess"+image);
        this.picUrl = image;
        this.canUploadTag = true;
        this.param.onGetPictureSuccess.apply(this, [image])
    },
    _onGetPictureFail: function(message) {
        //alert("_onGetPictureFail"+message);
        this.picUrl = null;
        this.canUploadTag = false;
        this.param.onGetPictureFail.apply(this, [message])
    },
    upload: function() {
        //alert("upload in");
        if (!this.canUploadTag) {
            this.param.uploadCantExe.apply(this);
            return
        }
        //alert("upload 2"+ this.picUrl);
        this.canUploadTag=false;
        var options = new FileUploadOptions();
        options.fileKey = this.param.uploadImageKey;
        options.fileName = this.picUrl.substr(this.picUrl.lastIndexOf('/') + 1);
        options.mimeType = "image/jpeg";
        options.params = this.param.uploadParam;
        var ft = new FileTransfer();
        ft.onprogress = this.param.onUploadProgress.bind(this);
          
        ft.upload(this.picUrl, this.param.uploadUrl, Util.proxy(this._uploadSuccess, this), Util.proxy(this._uploadFailed, this), options)
    },
    _uploadSuccess: function(r) {
        //alert("_uploadSuccess");
        if (r && r.response) {
            //alert("_uploadSuccess:"+JSON.parse(r.response));
            this.param.uploadSuccess.apply(this, [JSON.parse(r.response)])
        }
    },
    _uploadFailed: function(error) {
        //alert("_uploadFailed:"+error.code);
        var errorcode = error.code;
        var errstr = "";
        switch (errorcode) {
        case 1:
            {
                errstr = "错误代码1：源文件路径异常，请重新选择或者拍照上传！";
                break
            }
        case 2:
            {
                errstr = "错误代码2:目标地址无效,请重试！";
                break
            }
        case 3:
            {
                errstr = "您手机或者后台服务器网络异常,请重新上传！";
                break
            }
        default:
            {
                errstr = "程序出错";
                break
            }
        }
        error.errorMessage = errstr;
        this.param.uploadFailed.apply(this, [error])
    }
});