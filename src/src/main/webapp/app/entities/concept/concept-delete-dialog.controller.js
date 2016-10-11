(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ConceptDeleteController',ConceptDeleteController);

    ConceptDeleteController.$inject = ['$uibModalInstance', 'entity', 'taxpayer_account_entity', 'Concept'];

    function ConceptDeleteController($uibModalInstance, entity, taxpayer_account_entity, Concept) {
        var vm = this;

        vm.concept = entity;
        vm.taxpayer_account = taxpayer_account_entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Concept.delete({id: id, taxpayeraccount: vm.taxpayer_account.id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
