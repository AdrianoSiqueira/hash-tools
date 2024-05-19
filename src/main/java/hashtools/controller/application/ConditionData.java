package hashtools.controller.application;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
class ConditionData {

    private String field1Content;
    private String field2Content;
    private String areaStatusContent;

    private boolean checkUseFile1Selected;
    private boolean checkUseFile2Selected;

    private boolean buttonCheckSelected;
    private boolean buttonGenerateSelected;
    private boolean buttonCompareSelected;

    private boolean checkMD5Selected;
    private boolean checkSHA1Selected;
    private boolean checkSHA224Selected;
    private boolean checkSHA256Selected;
    private boolean checkSHA384Selected;
    private boolean checkSHA512Selected;
}
