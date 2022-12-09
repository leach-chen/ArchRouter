isRun=false
for line in `cat upload.properties`
do
  str=$line
  if [[ $str == "isPublish=true" ]]
  then
    isRun=true
    echo "当前打包的是正式环境的aar"
  elif [[ $str == "isPublish=false" ]]
  then
    isRun=true
    echo "当前打包的是测试环境的aar"
  fi
done

if [[ $isRun == false ]]
then
  echo "配置错误，请在upload.properties文件末尾配置isPublish=true或者isPublish=false"
#elif [[ "publish" == "$1" ]]
elif [[ $isRun == true ]]
then
    buildCmd="./gradlew :apt-arch:arch:clean :apt-arch:arch:build :apt-arch:arch:publish"
    $buildCmd
    buildCmd="./gradlew :apt-arch:arch-annotation:clean :apt-arch:arch-annotation:build :apt-arch:arch-annotation:publish"
    $buildCmd
    buildCmd="./gradlew :apt-arch:arch-compiler:clean :apt-arch:arch-compiler:build :apt-arch:arch-compiler:publish"
    $buildCmd
    buildCmd="./gradlew :router:clean :router:build router:publish"
    $buildCmd
    buildCmd="./gradlew :plugin:pluginSwitch:clean :plugin:pluginSwitch:build :plugin:pluginSwitch:publish"
    $buildCmd
    buildCmd="./gradlew :plugin:pluginRouter:clean :plugin:pluginRouter:build :plugin:pluginRouter:publish"
    $buildCmd
    echo "上传完成!"
fi
sleep 10000