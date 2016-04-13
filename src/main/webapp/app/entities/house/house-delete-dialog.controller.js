(function() {
    'use strict';

    angular
        .module('sirTp6App')
        .controller('HouseDeleteController',HouseDeleteController);

    HouseDeleteController.$inject = ['$uibModalInstance', 'entity', 'House'];

    function HouseDeleteController($uibModalInstance, entity, House) {
        var vm = this;
        vm.house = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            House.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
