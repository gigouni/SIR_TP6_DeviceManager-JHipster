(function() {
    'use strict';

    angular
        .module('sirTp6App')
        .controller('ElectronicDeviceDialogController', ElectronicDeviceDialogController);

    ElectronicDeviceDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ElectronicDevice', 'House'];

    function ElectronicDeviceDialogController ($scope, $stateParams, $uibModalInstance, entity, ElectronicDevice, House) {
        var vm = this;
        vm.electronicDevice = entity;
        vm.houses = House.query();
        vm.load = function(id) {
            ElectronicDevice.get({id : id}, function(result) {
                vm.electronicDevice = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('sirTp6App:electronicDeviceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.electronicDevice.id !== null) {
                ElectronicDevice.update(vm.electronicDevice, onSaveSuccess, onSaveError);
            } else {
                ElectronicDevice.save(vm.electronicDevice, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
