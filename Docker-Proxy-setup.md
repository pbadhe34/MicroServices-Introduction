 Edit/Create the file ~/.docker/config.json in the home directory of the user which starts containers. 

{
 "proxies":
 {
   "default":
   {
     "httpProxy": "http://10.19.16.165:8080",
     "httpsProxy": "http://10.19.16.165:8080",
     "noProxy": "localhost,10.19.41.61,127.0.0.1"
   }
 }
}


Reload the docker daemon
sudo systemctl stop docker

sudo systemctl status docker

sudo systemctl start docker

sudo systemctl status docker