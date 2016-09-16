(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_mail_accountsDeleteController',Taxpayer_mail_accountsDeleteController);

    Taxpayer_mail_accountsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Taxpayer_mail_accounts'];

    function Taxpayer_mail_accountsDeleteController($uibModalInstance, entity, Taxpayer_mail_accounts) {
        var vm = this;

        vm.taxpayer_mail_accounts = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Taxpayer_mail_accounts.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
