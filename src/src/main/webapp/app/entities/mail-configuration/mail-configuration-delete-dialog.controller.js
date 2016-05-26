(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Mail_configurationDeleteController',Mail_configurationDeleteController);

    Mail_configurationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Mail_configuration'];

    function Mail_configurationDeleteController($uibModalInstance, entity, Mail_configuration) {
        var vm = this;
        vm.mail_configuration = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Mail_configuration.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
