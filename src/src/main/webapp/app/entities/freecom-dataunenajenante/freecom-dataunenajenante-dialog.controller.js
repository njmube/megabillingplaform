(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_dataunenajenanteDialogController', Freecom_dataunenajenanteDialogController);

    Freecom_dataunenajenanteDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Freecom_dataunenajenante', 'Freecom_data_enajenante'];

    function Freecom_dataunenajenanteDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Freecom_dataunenajenante, Freecom_data_enajenante) {
        var vm = this;
        vm.freecom_dataunenajenante = entity;
        vm.freecom_data_enajenantes = Freecom_data_enajenante.query({filter: 'freecom_dataunenajenante-is-null'});
        $q.all([vm.freecom_dataunenajenante.$promise, vm.freecom_data_enajenantes.$promise]).then(function() {
            if (!vm.freecom_dataunenajenante.freecom_data_enajenante || !vm.freecom_dataunenajenante.freecom_data_enajenante.id) {
                return $q.reject();
            }
            return Freecom_data_enajenante.get({id : vm.freecom_dataunenajenante.freecom_data_enajenante.id}).$promise;
        }).then(function(freecom_data_enajenante) {
            vm.freecom_data_enajenantes.push(freecom_data_enajenante);
        });
        vm.load = function(id) {
            Freecom_dataunenajenante.get({id : id}, function(result) {
                vm.freecom_dataunenajenante = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_dataunenajenanteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_dataunenajenante.id !== null) {
                Freecom_dataunenajenante.update(vm.freecom_dataunenajenante, onSaveSuccess, onSaveError);
            } else {
                Freecom_dataunenajenante.save(vm.freecom_dataunenajenante, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
