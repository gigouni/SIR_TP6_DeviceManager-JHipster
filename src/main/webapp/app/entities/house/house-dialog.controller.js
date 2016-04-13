(function() {
    'use strict';

    angular
        .module('sirTp6App')
        .controller('HouseDialogController', HouseDialogController);

    HouseDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'House', 'Person', 'ElectronicDevice', 'Heater'];

    function HouseDialogController ($scope, $stateParams, $uibModalInstance, entity, House, Person, ElectronicDevice, Heater) {
        var vm = this;
        vm.house = entity;
        vm.persons = Person.query();
        vm.electronicdevices = ElectronicDevice.query();
        vm.heaters = Heater.query();
        vm.load = function(id) {
            House.get({id : id}, function(result) {
                vm.house = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('sirTp6App:houseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.house.id !== null) {
                House.update(vm.house, onSaveSuccess, onSaveError);
            } else {
                House.save(vm.house, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
