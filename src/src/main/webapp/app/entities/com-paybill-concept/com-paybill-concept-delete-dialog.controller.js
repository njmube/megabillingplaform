(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_paybill_conceptDeleteController',Com_paybill_conceptDeleteController);

    Com_paybill_conceptDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_paybill_concept'];

    function Com_paybill_conceptDeleteController($uibModalInstance, entity, Com_paybill_concept) {
        var vm = this;
        vm.com_paybill_concept = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_paybill_concept.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
