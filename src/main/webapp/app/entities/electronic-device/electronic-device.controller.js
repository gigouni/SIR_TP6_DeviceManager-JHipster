(function() {
    'use strict';

    angular
        .module('sirTp6App')
        .controller('ElectronicDeviceController', ElectronicDeviceController);

    ElectronicDeviceController.$inject = ['$scope', '$state', 'ElectronicDevice'];

    function ElectronicDeviceController ($scope, $state, ElectronicDevice) {
        var vm = this;
        vm.electronicDevices = [];
        vm.loadAll = function() {
            ElectronicDevice.query(function(result) {
                vm.electronicDevices = result;
            });
        };

        vm.loadAll();
        
    }
})();
