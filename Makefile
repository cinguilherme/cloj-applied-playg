### invoking java with configs
java:
	java -cp myapp.jar -Drule-set="experimental"

### with envs
java-e:
	export rule_set=experimental
	java -cp myapp.jar
