package za.co.mtn.bright;

public enum LBUrl {
	PROD("https://wcc-prod.mtn.co.za"),
	STAGE("https://wcc-stage.mtn.co.za"),
	DEV(null),
	QA("https://wcc-qa.mtn.co.za");
	
	private String lbUrl;

	LBUrl(String lbUrl) {
        this.lbUrl = lbUrl;
    }

    public String lbUrl() {
        return lbUrl;
    }
}
