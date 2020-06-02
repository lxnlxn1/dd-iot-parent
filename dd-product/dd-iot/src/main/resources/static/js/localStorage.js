const foowwLocalStorage = {
    set: function (key, value, ttl_ms) {
        var data = {value: value, expirse: new Date(ttl_ms).getTime()};
        localStorage.setItem(key, JSON.stringify(data));
    },
    get: function (key) {
        var data = JSON.parse(localStorage.getItem(key));
        if (data !== null) {
            if (data.expirse != null && data.expirse < new Date().getTime()) {
                localStorage.removeItem(key);
            } else {
                return data.value;
            }
        }
        return null;
    }
}