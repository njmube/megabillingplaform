(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_certificateDetailController', Taxpayer_certificateDetailController);

    Taxpayer_certificateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'Taxpayer_certificate'];

    function Taxpayer_certificateDetailController($scope, $rootScope, $stateParams, DataUtils, entity, Taxpayer_certificate) {
        var vm = this;

        vm.taxpayer_certificate = entity;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:taxpayer_certificateUpdate', function(event, result) {
            vm.taxpayer_certificate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
