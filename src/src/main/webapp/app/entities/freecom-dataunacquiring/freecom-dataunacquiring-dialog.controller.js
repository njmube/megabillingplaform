(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_dataunacquiringDialogController', Freecom_dataunacquiringDialogController);

    Freecom_dataunacquiringDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Freecom_dataunacquiring', 'Freecom_acquiring_data'];

    function Freecom_dataunacquiringDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Freecom_dataunacquiring, Freecom_acquiring_data) {
        var vm = this;
        vm.freecom_dataunacquiring = entity;
        vm.freecom_acquiring_datas = Freecom_acquiring_data.query({filter: 'freecom_dataunacquiring-is-null'});
        $q.all([vm.freecom_dataunacquiring.$promise, vm.freecom_acquiring_datas.$promise]).then(function() {
            if (!vm.freecom_dataunacquiring.freecom_acquiring_data || !vm.freecom_dataunacquiring.freecom_acquiring_data.id) {
                return $q.reject();
            }
            return Freecom_acquiring_data.get({id : vm.freecom_dataunacquiring.freecom_acquiring_data.id}).$promise;
        }).then(function(freecom_acquiring_data) {
            vm.freecom_acquiring_datas.push(freecom_acquiring_data);
        });
        vm.load = function(id) {
            Freecom_dataunacquiring.get({id : id}, function(result) {
                vm.freecom_dataunacquiring = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_dataunacquiringUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_dataunacquiring.id !== null) {
                Freecom_dataunacquiring.update(vm.freecom_dataunacquiring, onSaveSuccess, onSaveError);
            } else {
                Freecom_dataunacquiring.save(vm.freecom_dataunacquiring, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
