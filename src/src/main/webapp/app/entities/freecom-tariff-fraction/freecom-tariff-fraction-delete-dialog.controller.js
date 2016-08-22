(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_tariff_fractionDeleteController',Freecom_tariff_fractionDeleteController);

    Freecom_tariff_fractionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_tariff_fraction'];

    function Freecom_tariff_fractionDeleteController($uibModalInstance, entity, Freecom_tariff_fraction) {
        var vm = this;

        vm.freecom_tariff_fraction = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Freecom_tariff_fraction.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
