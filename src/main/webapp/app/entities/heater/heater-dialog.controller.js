(function() {
    'use strict';

    angular
        .module('sirTp6App')
        .controller('HeaterDialogController', HeaterDialogController);

    HeaterDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Heater', 'House'];

    function HeaterDialogController ($scope, $stateParams, $uibModalInstance, entity, Heater, House) {
        var vm = this;
        vm.heater = entity;
        vm.houses = House.query();
        vm.load = function(id) {
            Heater.get({id : id}, function(result) {
                vm.heater = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('sirTp6App:heaterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.heater.id !== null) {
                Heater.update(vm.heater, onSaveSuccess, onSaveError);
            } else {
                Heater.save(vm.heater, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
