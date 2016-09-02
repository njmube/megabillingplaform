(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_accountDeleteController',Taxpayer_accountDeleteController);

    Taxpayer_accountDeleteController.$inject = ['$uibModalInstance', 'entity', 'Taxpayer_account'];

    function Taxpayer_accountDeleteController($uibModalInstance, entity, Taxpayer_account) {
        var vm = this;

        vm.taxpayer_account = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Taxpayer_account.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
