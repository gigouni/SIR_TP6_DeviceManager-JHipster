(function() {
    'use strict';
    angular
        .module('sirTp6App')
        .factory('Heater', Heater);

    Heater.$inject = ['$resource'];

    function Heater ($resource) {
        var resourceUrl =  'api/heaters/:id';

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
