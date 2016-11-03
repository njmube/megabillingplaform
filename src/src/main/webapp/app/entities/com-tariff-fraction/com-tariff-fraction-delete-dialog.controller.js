(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_tariff_fractionDeleteController',Com_tariff_fractionDeleteController);

    Com_tariff_fractionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_tariff_fraction'];

    function Com_tariff_fractionDeleteController($uibModalInstance, entity, Com_tariff_fraction) {
        var vm = this;
        vm.com_tariff_fraction = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_tariff_fraction.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
