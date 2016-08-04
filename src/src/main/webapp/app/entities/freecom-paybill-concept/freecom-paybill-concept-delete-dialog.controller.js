(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_paybill_conceptDeleteController',Freecom_paybill_conceptDeleteController);

    Freecom_paybill_conceptDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_paybill_concept'];

    function Freecom_paybill_conceptDeleteController($uibModalInstance, entity, Freecom_paybill_concept) {
        var vm = this;
        vm.freecom_paybill_concept = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_paybill_concept.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
