(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_conceptDeleteController',Taxpayer_conceptDeleteController);

    Taxpayer_conceptDeleteController.$inject = ['$uibModalInstance', 'entity', 'taxpayer_account_entity', 'Taxpayer_concept'];

    function Taxpayer_conceptDeleteController($uibModalInstance, entity, taxpayer_account_entity, Taxpayer_concept) {
        var vm = this;
        vm.taxpayer_concept = entity;
        vm.taxpayer_account = taxpayer_account_entity;

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.confirmDelete = function (id) {
            Taxpayer_concept.delete({id: id, taxpayeraccount: vm.taxpayer_account.id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
