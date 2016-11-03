(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_ecc_11_conceptDeleteController',Com_ecc_11_conceptDeleteController);

    Com_ecc_11_conceptDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_ecc_11_concept'];

    function Com_ecc_11_conceptDeleteController($uibModalInstance, entity, Com_ecc_11_concept) {
        var vm = this;
        vm.com_ecc_11_concept = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_ecc_11_concept.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
