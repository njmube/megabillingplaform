(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_ecc_11DeleteController',Com_ecc_11DeleteController);

    Com_ecc_11DeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_ecc_11'];

    function Com_ecc_11DeleteController($uibModalInstance, entity, Com_ecc_11) {
        var vm = this;
        vm.com_ecc_11 = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_ecc_11.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
