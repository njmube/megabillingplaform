(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_destruction_certificateDetailController', Freecom_destruction_certificateDetailController);

    Freecom_destruction_certificateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_destruction_certificate', 'Free_cfdi', 'C_type_series'];

    function Freecom_destruction_certificateDetailController($scope, $rootScope, $stateParams, entity, Freecom_destruction_certificate, Free_cfdi, C_type_series) {
        var vm = this;

        vm.freecom_destruction_certificate = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_destruction_certificateUpdate', function(event, result) {
            vm.freecom_destruction_certificate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
