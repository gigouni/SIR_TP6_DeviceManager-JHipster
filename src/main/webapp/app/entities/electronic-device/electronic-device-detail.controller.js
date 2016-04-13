(function() {
    'use strict';

    angular
        .module('sirTp6App')
        .controller('ElectronicDeviceDetailController', ElectronicDeviceDetailController);

    ElectronicDeviceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'ElectronicDevice', 'House'];

    function ElectronicDeviceDetailController($scope, $rootScope, $stateParams, entity, ElectronicDevice, House) {
        var vm = this;
        vm.electronicDevice = entity;
        vm.load = function (id) {
            ElectronicDevice.get({id: id}, function(result) {
                vm.electronicDevice = result;
            });
        };
        var unsubscribe = $rootScope.$on('sirTp6App:electronicDeviceUpdate', function(event, result) {
            vm.electronicDevice = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
