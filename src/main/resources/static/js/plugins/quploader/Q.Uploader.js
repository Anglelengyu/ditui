//devin87@qq.com
//build:2018/11/07 13:24:27
!function(r,d){"use strict";var i=Q.def,o=Q.fire,s=Q.extend,c=Q.getFirst,p=Q.getLast,u=JSON.parse,f=Q.createEle,n=Q.parseHTML,h=Q.setOpacity,l=Q.getOffset,m=Q.md5File,e=Q.event,v=e.add,g=e.trigger,_=e.stop,y=!1,w=!1,T=!1,a=0,k=0,x=0,S=-1,I={};function b(e,t){var r=e.lastIndexOf(t);return-1!=r?e.slice(r):""}function E(e){if(e){for(var t=e.split(","),r={},a=0,i=t.length;a<i;a++)r[t[a]]=!0;return r}}function z(e){var t=this,r=e||{};t.guid=r.guid||"uploader"+ ++a,t.list=[],t.map={},t.index=0,t.started=!1,t.set(r)._init()}z.prototype={constructor:z,set:function(e){var t=this,r=s(e,t.ops);t.url=r.url,t.dataType=r.dataType||"json",t.data=r.data,t.targets=r.target||[],t.targets.forEach||(t.targets=[t.targets]),t.target=t.targets[0],t.html5=y&&!!i(r.html5,!0),t.multiple=w&&t.html5&&!!i(r.multiple,!0),t.clickTrigger=T&&!!i(r.clickTrigger,!0),t.workerThread=t.html5&&r.workerThread||1,t.workerIdle=t.workerThread,t.auto=!1!==r.auto,t.upName=r.upName||"upfile",t.accept=r.accept||r.allows,t.isDir=r.isDir,t.allows=E(r.allows),t.disallows=E(r.disallows),t.maxSize=+r.maxSize||0,t.isSlice=!!r.isSlice,t.chunkSize=+r.chunkSize||2097152,t.isQueryState=!!i(r.isQueryState,t.isSlice),t.isMd5=!!i(r.isMd5,t.isSlice),t.isUploadAfterHash=!1!==r.isUploadAfterHash,t.sliceRetryCount=r.sliceRetryCount==d?2:+r.sliceRetryCount||0,t.container=r.container||document.body,r.getPos&&(t.getPos=r.getPos);var a=r.UI||{};return a.init&&(t.init=a.init),a.draw&&(t.draw=a.draw),a.update&&(t.update=a.update),a.over&&(t.over=a.over),t.fns=r.on||{},t.ops=r,t.accept&&!t.clickTrigger&&t.resetInput(),t},_init:function(){var t=this;if(!t._inited){t._inited=!0;var e,r,a=t.guid,i=t.container,s=f("div","upload-input "+a);if(i.appendChild(s),t.boxInput=s,!t.html5){var n="upload_iframe_"+a,o=f("div","upload-html4 "+a,'<iframe class="u-iframe" name="'+n+'"></iframe><form class="u-form" action="" method="post" enctype="multipart/form-data" target="'+n+'"></form>');i.appendChild(o);var u=c(o),l=p(o);t.iframe=u,t.form=l,r=function(){if(0==t.workerIdle){var e;try{e=u.contentWindow.document.body.innerHTML}catch(e){}t.complete(d,2,e)}},(e=u).attachEvent?e.attachEvent("onload",r):e.addEventListener("load",r,!1)}return t.targets.forEach(function(e){t.clickTrigger?v(e,"click",function(e){!1!==t.fire("select",e)&&(t.resetInput(),g(t.inputFile,"click"))}):v(e,"mouseover",function(e){t.target=this,t.updatePos()})}),t.clickTrigger||(v(s,"click",function(e){!1===t.fire("select",e)&&_(e)}),h(s,0),t.resetInput()),t.fire("init"),t.run("init")}},resetInput:function(){var t=this,e=t.boxInput;if(!e)return t;e.innerHTML='<input type="file" name="'+t.upName+'"'+(t.accept?'accept="'+t.accept+'"':"")+(t.isDir?'webkitdirectory=""':"")+' style="'+(t.clickTrigger?"visibility: hidden;":"font-size:100px;")+'"'+(t.multiple?' multiple="multiple"':"")+">";var r=c(e);return v(r,"change",function(e){t.add(this),t.html5||t.resetInput()}),t.inputFile=r,t.updatePos()},updatePos:function(e){var t=this;if(t.clickTrigger)return t;var r=t.getPos||l,a=t.boxInput,i=c(a),s=t.target,n=s.offsetWidth,o=s.offsetHeight,u=0==n?{left:-1e4,top:-1e4}:r(s);return a.style.width=i.style.width=n+"px",a.style.height=i.style.height=o+"px",a.style.left=u.left+"px",a.style.top=u.top+"px",e&&(a.style.zIndex=++x),t},fire:function(e,t,r){if(!r)return o(this.fns[e],this,t);var a=this.fns[e+"Async"];if(a)return o(a,this,t,r);r(o(this.fns[e],this,t))},run:function(e,t){var r=this[e];return r&&o(r,this,t),this},addTask:function(e,t){if(e||t){var r,a;a=t?(r=t.webkitRelativePath||t.name||t.fileName,0===t.size?0:t.size||t.fileSize):(r=b(e.value,"\\").slice(1)||e.value,-1);var i,s=this,n=b(r,".").toLowerCase();s.disallows&&s.disallows[n]||s.allows&&!s.allows[n]?i="ext":-1!=a&&s.maxSize&&a>s.maxSize&&(i="size");var o={id:++k,name:r,ext:n,size:a,input:e,file:t,state:i?S:0};return i&&(o.limited=i,o.disabled=!0),s.fire("add",o,function(e){!1===e||o.disabled||o.limited||(o.index=s.list.length,s.list.push(o),s.map[o.id]=o,s.run("draw",o),s.auto&&s.start())}),o}},add:function(e){if("INPUT"==e.tagName){var t=e.files;if(t)for(var r=0,a=t.length;r<a;r++)this.addTask(e,t[r]);else this.addTask(e)}else this.addTask(d,e)},addList:function(e){for(var t=0,r=e.length;t<r;t++)this.add(e[t])},get:function(e){if(e!=d)return this.map[e]},cancel:function(e,t){var r=this,a=r.get(e);if(a){var i=a.state;if(0!=i&&1!=i)return r;if(1==i){var s=a.xhr;if(s)return s.abort(),r;r.iframe.contentWindow.location="about:blank"}return t?r:r.complete(a,-2)}},remove:function(e){var t=this.get(e);t&&(1==t.state&&this.cancel(e),t.deleted=!0,this.fire("remove",t))},start:function(){var e=this,t=e.workerIdle,r=e.list,a=e.index,i=r.length;if(e.started||(e.started=!0),i<=0||i<=a||t<=0)return e;var s=r[a];return e.index++,e.upload(s)},upload:function(t){var r=this;return!t||0!=t.state||t.skip||t.deleted?r.start():(t.url=r.url,r.workerIdle--,r.fire("upload",t,function(e){if(!1===e)return r.complete(t,S);r.html5&&t.file?r._upload_html5_ready(t):t.input?r._upload_html4(t):r.complete(t,S)}),r)},_process_xhr_headers:function(r){var e=this.ops,t=function(e,t){r.setRequestHeader(e,t)};I.headers&&Object.forEach(I.headers,t),e.headers&&Object.forEach(e.headers,t)},queryState:function(a,i){var s=this,e=s.url,n=new XMLHttpRequest,r=["action=query","hash="+(a.hash||encodeURIComponent(a.name)),"fileName="+encodeURIComponent(a.name)];return-1!=a.size&&r.push("fileSize="+a.size),s._process_params(a,function(e,t){r.push(encodeURIComponent(e)+"="+(t!=d?encodeURIComponent(t):""))},"dataQuery"),a.queryUrl=e+(-1==e.indexOf("?")?"?":"&")+r.join("&"),s.fire("sliceQuery",a),n.open("GET",a.queryUrl),s._process_xhr_headers(n),n.onreadystatechange=function(){if(4==n.readyState){var e,t;if(200<=n.status&&n.status<400)if("ok"===(e=n.responseText)?t={ret:1}:e&&(t=u(e)),t&&"number"!=typeof t||(t={ret:0,start:t}),a.response=e,1==(a.json=t).ret)a.queryOK=!0,s.cancel(a.id,!0).complete(a,2);else{var r=+t.start||0;r!=Math.floor(r)&&(r=0),a.sliceStart=r}o(i,s,n)}},n.onerror=function(){o(i,s,n)},n.send(null),s},_upload_html5_ready:function(a){var i=this,e=function(){2!=a.state&&(i.isSlice?i._upload_slice(a):i._upload_html5(a))},s=function(e){i.fire("hash",a,function(){a.hash&&i.isQueryState&&2!=a.state?i.queryState(a,e):e()})},t=function(r){if(i.isMd5&&m){var t=i.fns.hashProgress;m(a.file,function(e,t){a.hash=e,a.timeHash=t,s(r)},function(e){o(t,i,a,e)})}else s(r)};return i.isUploadAfterHash?t(e):(e(),t()),i},_process_params:function(e,t,r){r=r||"data",I.data&&Object.forEach(I.data,t),this.data&&Object.forEach(this.data,t),e&&e[r]&&Object.forEach(e[r],t)},_upload_html5:function(t){var r=this,a=new XMLHttpRequest;(t.xhr=a).upload.addEventListener("progress",function(e){r.progress(t,e.total,e.loaded)},!1),a.addEventListener("load",function(e){r.complete(t,2,e.target.responseText)},!1),a.addEventListener("error",function(){r.complete(t,-3)},!1),a.addEventListener("abort",function(){r.complete(t,-2)},!1);var i=new FormData;r._process_params(t,function(e,t){i.append(e,t)}),i.append("fileName",t.name),i.append(r.upName,t.blob||t.file,t.name),a.open("POST",t.url),r._process_xhr_headers(a),r.fire("send",t,function(e){if(!1===e)return r.complete(t,S);a.send(i),r._afterSend(t)})},_upload_html4:function(t){var r=this,a=r.form,e=t.input;if(e._uploaded)return r.complete(t,2);e._uploaded=!0,e.name=r.upName,a.innerHTML="",a.appendChild(e),a.action=t.url,r._process_params(t,function(e,t){a.appendChild(n('<input type="hidden" name="'+e+'" value="'+t+'">'))}),r.fire("send",t,function(e){if(!1===e)return r.complete(t,S);a.submit(),r._afterSend(t)})},_afterSend:function(e){e.lastTime=e.startTime=Date.now(),e.state=1,this._lastTask=e,this.progress(e)},progress:function(e,t,r){t||(t=e.size),(!r||r<0)&&(r=0);var a=e.state||0;t<r&&(r=t),0<r&&0==a&&(e.state=a=1),2==a&&(t=r=e.size),function(e,t,r){if(t&&!(t<=0)){var a,i=Date.now();if(t<=r)return(a=i-e.startTime)?e.avgSpeed=Math.min(Math.round(1e3*t/a),t):e.speed||(e.avgSpeed=e.speed=t),e.time=a||0,e.endTime=i;(a=i-e.lastTime)<200||(e.speed=Math.min(Math.round(1e3*(r-e.loaded)/a),e.total),e.lastTime=i)}}(e,t,r),e.total=t,e.loaded=r,this.fire("progress",e),this.run("update",e)},_process_response:function(e,t){(e.response=t)&&"json"==this.dataType&&(e.json=u(t))},complete:function(e,t,r){var a=this;return e||1!=a.workerThread||(e=a._lastTask),e&&(t!=d&&(e.state=t),1!=e.state&&2!=t||(e.state=2,a.progress(e,e.size,e.size)),r!==d&&a._process_response(e,r)),a.run("update",e).run("over",e),-2==t&&a.fire("cancel",e),a.fire("complete",e),a.workerIdle++,a.started&&a.start(),a}},z.extend=function(e,t){s(z.prototype,e,t)},function(){var e=r.XMLHttpRequest;e&&(new e).upload&&r.FormData&&(y=!0);var t=document.createElement("input");t.type="file",w=!!t.files,T=y}(),s(z,{support:{html5:y,multiple:w},READY:0,PROCESSING:1,COMPLETE:2,SKIP:S,CANCEL:-2,ERROR:-3,UI:{},Lang:{status_ready:"准备中",status_processing:"上传中",status_complete:"已完成",status_skip:"已跳过",status_cancel:"已取消",status_error:"已失败"},setup:function(e){s(I,e,!0)},getStatusText:function(e){var t=z.Lang;switch(e){case 0:return t.status_ready;case 1:return t.status_processing;case 2:return t.status_complete;case S:return t.status_skip;case-2:return t.status_cancel;case-3:return t.status_error}return e}}),Q.Uploader=z}(window);