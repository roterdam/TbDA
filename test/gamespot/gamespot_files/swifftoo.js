function makeExpressInstall(B,C){var G=C+"express_install";var F=$(C);var E=F.width;var A=F.height>150?F.height:145;B.removeChild(F);var H=Browser.Engine.trident&&Browser.Platform.win?"ActiveX":"PlugIn";var D=new Swiff(expressInstallPath,{id:G,width:E,height:A,container:B,params:{},vars:{MMredirectURL:window.location.href,MMplayerType:H,MMdoctitle:document.title}});}function processFlashEmbed(A){var B=$(A).getJSONData();if(B.swiffVerify){if(!verifyFlashVersion(B.swiffVerify.version)){if(Browser.Plugins.Flash.version>6||(Browser.Plugins.Flash.version==6&&Browser.Plugins.Flash.build>=65)){makeExpressInstall(A,B.swiffVerify.flash_dom_id);
}else{if(B.swiffVerify.alt_content){A.dispose();$(B.swiffVerify.alt_content).setStyle("display","block");}}}}}$$(".swiff_container").each(function(A){processFlashEmbed(A);});