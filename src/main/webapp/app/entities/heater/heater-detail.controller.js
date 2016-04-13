(function() {
    'use strict';

    angular
        .module('sirTp6App')
        .controller('HeaterDetailController', HeaterDetailController);

    HeaterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Heater', 'House'];

    function HeaterDetailController($scope, $rootScope, $stateParams, entity, Heater, House) {
        var vm = this;
        vm.heater = entity;
        vm.load = function (id) {
            Heater.get({id: id}, function(result) {
                vm.heater = result;
            });
        };
        var unsubscribe = $rootScope.$on('sirTp6App:heaterUpdate', function(event, result) {
            vm.heater = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
