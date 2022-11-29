#!/bin/ksh

clear 

. ./.entorno


fechaHoraLog=`date '+%d/%m/%y %H:%M%n'`
fecha=`date '+%y_%m_%d'`

#echo "$fechaHoraLog: -----------------" > ${LOG_EJEC}/${fecha}_LanzaProceso.log
#echo "$fechaHoraLog: INICIO $1:$2 ($3)" >> ${LOG_EJEC}/${fecha}_LanzaProceso.log

#echo "-----------------"
#echo "Arrancando la clase java ..."
# echo "-----------------"
# echo "-----------------"
# echo "CLASSPATH="${CLASSPATH_CP}
# echo ""
# echo "PATH="${PATH_CP}
# echo ""
# echo "LD_LIBRARY_PATH="${LD_LIBRARY_PATH_CP}
# echo ""
# echo "JAVA_ARGS="${JAVA_ARGS}
# echo "-----------------"
#echo "-----------------"
#echo ""
#echo ""



#echo "-----------------"
#echo java ${MEM_ARGS} ${JAVA_ARGS} -jar LanzaProceso.jar "$1" "$2" "$3" "$4" #1>> ${LOG_EJEC}/${fecha}_LanzaProceso.log 2>>${LOG_EJEC}/${fecha}_LanzaProceso.log
#echo "-----------------"
#echo ""
#echo ""
${JAVA_HOME}/bin/java ${MEM_ARGS} ${JAVA_ARGS} -jar LanzaProceso.jar "$1" "$2" "$3" "$4" #1>> ${LOG_EJEC}/${fecha}_LanzaProceso.log 2>>${LOG_EJEC}/${fecha}_LanzaProceso.log


# if [ -f ExecuteAIF.class ] 
# then
  # ${JAVA_HOME}/bin/java ${MEM_ARGS} ${JAVA_ARGS} ExecuteAIF "$1" "$2" "$3" "$4" #1>> ${LOG_EJEC}/${fecha}_LanzaProceso.log 2>>${LOG_EJEC}/${fecha}_LanzaProceso.log

# else
  # echo "ERROR. No se ha encontrado la clase."
# fi

salida=$?

fechaHoraLog=`date '+%d/%m/%y %H:%M%n'`
#echo "$fechaHoraLog: FIN $1:$2 ($3)" >> ${LOG_EJEC}/${fecha}_LanzaProceso.log
#echo "$fechaHoraLog: -----------------" >> ${LOG_EJEC}/${fecha}_LanzaProceso.log


exit $salida


