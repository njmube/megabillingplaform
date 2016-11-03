(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_chargeDeleteController',Com_chargeDeleteController);

    Com_chargeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_charge'];

    function Com_chargeDeleteController($uibModalInstance, entity, Com_charge) {
        var vm = this;
        vm.com_charge = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_charge.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
