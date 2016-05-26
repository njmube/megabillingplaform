(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Mail_configurationDialogController', Mail_configurationDialogController);

    Mail_configurationDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Mail_configuration'];

    function Mail_configurationDialogController ($scope, $stateParams, $uibModalInstance, entity, Mail_configuration) {
        var vm = this;
        vm.mail_configuration = entity;
        vm.load = function(id) {
            Mail_configuration.get({id : id}, function(result) {
                vm.mail_configuration = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:mail_configurationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.mail_configuration.id !== null) {
                Mail_configuration.update(vm.mail_configuration, onSaveSuccess, onSaveError);
            } else {
                Mail_configuration.save(vm.mail_configuration, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
