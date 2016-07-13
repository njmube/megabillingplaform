(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_chargeDeleteController',Freecom_chargeDeleteController);

    Freecom_chargeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_charge'];

    function Freecom_chargeDeleteController($uibModalInstance, entity, Freecom_charge) {
        var vm = this;
        vm.freecom_charge = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_charge.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
