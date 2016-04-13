(function() {
    'use strict';
    angular
        .module('sirTp6App')
        .factory('House', House);

    House.$inject = ['$resource'];

    function House ($resource) {
        var resourceUrl =  'api/houses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
