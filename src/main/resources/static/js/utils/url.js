const URL_COR_PREFIX = "/corporation/";
const URL_API_COR_PREFIX = "/api" + URL_COR_PREFIX;

const PATH_COR = "corporation";

function getNextPath(url, path){
    const list = url.split('/');
    const path_index = list.indexOf(path);
    let next_path;

    if(path_index < list.length){
        next_path = list[path_index + 1];
    }

    return next_path;
}