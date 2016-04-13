(function() {
    'use strict';

    angular
        .module('sirTp6App')
        .controller('HeaterDeleteController',HeaterDeleteController);

    HeaterDeleteController.$inject = ['$uibModalInstance', 'entity', 'Heater'];

    function HeaterDeleteController($uibModalInstance, entity, Heater) {
        var vm = this;
        vm.heater = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Heater.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
