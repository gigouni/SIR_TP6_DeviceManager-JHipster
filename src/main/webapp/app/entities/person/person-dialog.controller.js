(function() {
    'use strict';

    angular
        .module('sirTp6App')
        .controller('PersonDialogController', PersonDialogController);

    PersonDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Person', 'House'];

    function PersonDialogController ($scope, $stateParams, $uibModalInstance, entity, Person, House) {
        var vm = this;
        vm.person = entity;
        vm.houses = House.query();
        vm.load = function(id) {
            Person.get({id : id}, function(result) {
                vm.person = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('sirTp6App:personUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.person.id !== null) {
                Person.update(vm.person, onSaveSuccess, onSaveError);
            } else {
                Person.save(vm.person, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
