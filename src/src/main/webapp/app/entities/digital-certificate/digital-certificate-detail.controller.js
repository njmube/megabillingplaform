(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Digital_certificateDetailController', Digital_certificateDetailController);

    Digital_certificateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'Digital_certificate'];

    function Digital_certificateDetailController($scope, $rootScope, $stateParams, DataUtils, entity, Digital_certificate) {
        var vm = this;
        vm.digital_certificate = entity;
        vm.load = function (id) {
            Digital_certificate.get({id: id}, function(result) {
                vm.digital_certificate = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:digital_certificateUpdate', function(event, result) {
            vm.digital_certificate = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
    }
})();
