if(!(htr('hittail_ok'))){htc=document.referrer;htz=htl(document.location.href);if(htz!=htl(htc)&&(htc!='')){if(htw(htc)){kw=htq(htc);htAdt=htAdTk(document.location.href);document.write('<img src="http://tracking.hittail.com/mlt.png?ref='+escape(htc)+'&kw='+kw+'&eng='+htm(htc)+'&p='+htF(htc)+'&n='+htn(kw)+'&adt='+htAdt+'" width="1" height="1"/>');}}hty('hittail_ok','1','','/',document.domain,'');}function htw(hte){var htd=true;var htj=new Array("http://private.","http://internal.","http://intranet.","login=","/login","login.","logon=","/logon","logon.","/signin","signin=","signin.","signon","/admin/","mail.","/mail/","/email/","webmail","mailbox","https://","cache:","http://www.blogger.com","http://localhost","http://client.","http://docs.","http://timebase.","http://www2.blogger.","http://www.typepad.com/t/app/","http://www.typepad.com/t/comments","http://blockedReferrer");for(i=0;i<htj.length;i++){if(hte.search(htj[i])> -1){htd=false;return htd;}}var htE=/https?:\/\/(www\.|\d+\.)?hittail\.com/;var hto=/https?:\/\/(www\.|\d+\.)?mylongtail\.com/;if(hte.search(htE)> -1||hte.search(hto)> -1){htd=false;}return htd;};function htk(hta){return unescape(hta.replace(/\+/g," "));};function htm(url){var htH=/(http:\/\/)([^\/]*?)(\/)/;htH.test(url);hti=RegExp.$2;return hti;};function htq(url){var htG=/(\?|&|&amp;|;){1}(q|p|query|t|w|search|as_q|wd){1}=(.[^&=]*)=?/i;htG.test(url);kw=htk(RegExp.$3);if(kw.indexOf('cache:')>=0||kw.indexOf('http://')>=0||kw.indexOf('invocationType')>=0|| !isNaN(kw)){kw='';}else{kw=htD(kw);}return kw;};function htD(hta){hta=hta.replace(/[^\w #\.\-^\u00c0-\u00ff]/g,'');return hta;};function htF(url){p=0;htI=/\.google\./;if(htI.test(url)){htJ=/google(.*?)(start=)([0-9]+)/;htJ.test(url);p=RegExp.$3;if(p!=''){p=p/10;}else{p=1;}}if(p==0||p==''){hts=/\.yahoo\./;if(hts.test(url)){htt=/yahoo(.*?)b=([0-9]+)/;htt.test(url);p=RegExp.$2;if(p!=''){p=(p-1)/10+1;}else{p=1;}}}if(p==0||p==''){htg=/msn|live\.com/;if(htg.test(url)){htp=/msn|live\.com(.*?)(\?|&)first=([0-9]+)/;htp.test(url);p=RegExp.$3;if(p!=''){p=(p-1)/10+1;}else{p=1;}}}if(p==0||p==''){htg=/ask\.com/;if(htg.test(url)){htB=/ask(.*?)(\?|&)page=([0-9]+)/;htB.test(url);p=RegExp.$3;if(p!=''){p=(p-1)/10;}else{p=1;}}}return p;};function htn(hta){hta=hta.replace(/^\s+|\s+$/g,'');var htv=hta.split(/\s/);w=htv.length;return w;};function hty(name,value,expires,htf,domain,hth){var htx=name+"="+escape(value)+((htf)?"; htf="+htf:"")+((domain)?"; domain="+domain:"")+((hth)?"; hth":"");document.cookie=htx;};function htr(name){var dc=document.cookie;var prefix=name+"=";var htb=dc.indexOf("; "+prefix);if(htb== -1){htb=dc.indexOf(prefix);if(htb!=0)return null;}else htb+=2;var end=document.cookie.indexOf(";",htb);if(end== -1)end=dc.length;return unescape(dc.substring(htb+prefix.length,end));};function htl(url){var htb,end;htb=url.indexOf('//')+2;if(url.indexOf('/',8)){end=url.indexOf('/',8);}else{end=url.length}return url.substring(htb,end);};function htAdTk(url)
{if ( (url.indexOf('gclid') > 0) || (url.indexOf('ysmkey') > 0) || (url.indexOf('OVRAW') > 0) || (url.indexOf('OVKEY') > 0) ){ad = 1;}else{ad = 0;}return ad;}
