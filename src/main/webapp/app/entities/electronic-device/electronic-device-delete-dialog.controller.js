(function() {
    'use strict';

    angular
        .module('sirTp6App')
        .controller('ElectronicDeviceDeleteController',ElectronicDeviceDeleteController);

    ElectronicDeviceDeleteController.$inject = ['$uibModalInstance', 'entity', 'ElectronicDevice'];

    function ElectronicDeviceDeleteController($uibModalInstance, entity, ElectronicDevice) {
        var vm = this;
        vm.electronicDevice = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            ElectronicDevice.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
