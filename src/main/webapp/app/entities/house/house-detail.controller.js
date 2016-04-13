(function() {
    'use strict';

    angular
        .module('sirTp6App')
        .controller('HouseDetailController', HouseDetailController);

    HouseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'House', 'Person', 'ElectronicDevice', 'Heater'];

    function HouseDetailController($scope, $rootScope, $stateParams, entity, House, Person, ElectronicDevice, Heater) {
        var vm = this;
        vm.house = entity;
        vm.load = function (id) {
            House.get({id: id}, function(result) {
                vm.house = result;
            });
        };
        var unsubscribe = $rootScope.$on('sirTp6App:houseUpdate', function(event, result) {
            vm.house = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
