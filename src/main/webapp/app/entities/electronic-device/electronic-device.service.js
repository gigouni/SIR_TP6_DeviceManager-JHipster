(function() {
    'use strict';
    angular
        .module('sirTp6App')
        .factory('ElectronicDevice', ElectronicDevice);

    ElectronicDevice.$inject = ['$resource'];

    function ElectronicDevice ($resource) {
        var resourceUrl =  'api/electronic-devices/:id';

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
