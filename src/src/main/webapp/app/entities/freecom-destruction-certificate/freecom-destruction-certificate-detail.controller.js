(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_destruction_certificateDetailController', Freecom_destruction_certificateDetailController);

    Freecom_destruction_certificateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_destruction_certificate', 'C_class'];

    function Freecom_destruction_certificateDetailController($scope, $rootScope, $stateParams, entity, Freecom_destruction_certificate, C_class) {
        var vm = this;
        vm.freecom_destruction_certificate = entity;
        vm.load = function (id) {
            Freecom_destruction_certificate.get({id: id}, function(result) {
                vm.freecom_destruction_certificate = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_destruction_certificateUpdate', function(event, result) {
            vm.freecom_destruction_certificate = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
