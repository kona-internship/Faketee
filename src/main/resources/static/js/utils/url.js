/**
 * 회사 url path이다.
 *
 * @type {string}
 */
const PATH_COR = "/corporation";
/**
 * 출퇴근 장소
 * @type {string}
 */
const PATH_LOC = "/loc";
/**
 * 직무
 * @type {string}
 */
const PATH_POS = "/pos";
/**
 * 조직 url path이다.
 *
 * @type {string}
 */
const PATH_DEP = "/dep";
/**
 * 근무일정 url path이다.
 * @type {string}
 */
const PATH_SCH = "/sch";

/**
 * 근무일정 템플릿 url path
 */
const PATH_TMP = "/temp";

/**
 * 직원 url path이다.
 *
 * @type {string}
 */
const PATH_EMP = "/emp";

/**
 * 휴가 url path
 */
const PATH_VAC = "/vac";

const PATH_VAC_GROUP = "/vac/group";
const PATH_VAC_TYPE = "/vac/type";
const PATH_VAC_INFO = "/vac/info";
const PATH_VAC_REQ = "/vac/req";

/**
 * 근태 url path이다.
 * @type {string}
 */
const PATH_ATD = "/atd";

/**
 * 출퇴근기록 요청 url path이다.
 * @type {string}
 */
const PATH_ATD_REQ = "/atd/req";

/**
 * 기안 url path이다.
 *
 * @type {string}
 */
const PATH_DRAFT = "/draft";

/**
 * 기안의 승인 url path이다.
 *
 * @type {string}
 */
const PATH_DRAFT_APVL = "/apvl";

/**
 * 기안의 요청 url path이다.
 *
 * @type {string}
 */
const PATH_DRAFT_REQ = "/req";

/**
 * 기안의 완료 url path이다.
 *
 * @type {string}
 */
const PATH_DRAFT_DONE = "/done";


/**
 * 회사 선택 후 url에 들어가는 prefix이다.
 *
 * @type {string}
 */
const URL_COR_PREFIX = PATH_COR;

/**
 * 회사 선택후 Api 통신시 url에 들어가는 prefix이다.
 *
 * @type {string}
 */
const URL_API_COR_PREFIX = "/api" + URL_COR_PREFIX;


/**
 * url의 다음 path를 리턴한다.
 * 전체 url에서 파라미터로 받은 path의 다음 path를 리턴해준다.
 * '/'을 구분자로 다음 path를 가져온다.
 *
 * @param url 전체 url
 * @param path 가져오고자 하는 path의 전 path
 * @returns {*}
 */
function getNextPath(url, path){

    const pathList = path.split('/');
    const urlList = url.split('/');
    const path_index = urlList.indexOf(pathList[pathList.length-1]);
    let next_path;

    if(path_index < urlList.length){
        next_path = urlList[path_index + 1];
    }

    return "/" + next_path;
}