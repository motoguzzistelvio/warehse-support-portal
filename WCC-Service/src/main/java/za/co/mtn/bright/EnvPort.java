package za.co.mtn.bright;

public enum EnvPort {
	PROD("1234"),
	STAGE("456"),
	DEV("20101"),
	QA("20801");
	
	private String port;

	EnvPort(String port) {
        this.port = port;
    }

    public String port() {
        return port;
    }

}
