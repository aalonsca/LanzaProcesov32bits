#!/bin/ksh
JAVA_HOME="/usr/java/jdk1.6.0_24.32b"
PROC_CLASSPATH=":.:/amxusers2/test/crm/chicrm4/CRM/LanzaProceso_v32bits/lib/*"
OUT="/amxusers2/test/crm/chicrm4/CRM/LanzaProceso_v32bits/classes"


echo javac -cp ${CLASS_PATH}:${PROC_CLASSPATH}
${JAVA_HOME}/bin/javac -cp ${CLASS_PATH}:${PROC_CLASSPATH} -d ${OUT} $(find . -name "*.java")
