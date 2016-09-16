(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_ftp_accountDeleteController',Taxpayer_ftp_accountDeleteController);

    Taxpayer_ftp_accountDeleteController.$inject = ['$uibModalInstance', 'entity', 'Taxpayer_ftp_account'];

    function Taxpayer_ftp_accountDeleteController($uibModalInstance, entity, Taxpayer_ftp_account) {
        var vm = this;

        vm.taxpayer_ftp_account = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Taxpayer_ftp_account.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
