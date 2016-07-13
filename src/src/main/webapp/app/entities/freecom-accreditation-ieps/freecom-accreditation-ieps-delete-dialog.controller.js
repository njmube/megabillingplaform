(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_accreditation_iepsDeleteController',Freecom_accreditation_iepsDeleteController);

    Freecom_accreditation_iepsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_accreditation_ieps'];

    function Freecom_accreditation_iepsDeleteController($uibModalInstance, entity, Freecom_accreditation_ieps) {
        var vm = this;
        vm.freecom_accreditation_ieps = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_accreditation_ieps.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
