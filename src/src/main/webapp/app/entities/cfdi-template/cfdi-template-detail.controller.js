(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Cfdi_templateDetailController', Cfdi_templateDetailController);

    Cfdi_templateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'Cfdi_template'];

    function Cfdi_templateDetailController($scope, $rootScope, $stateParams, DataUtils, entity, Cfdi_template) {
        var vm = this;
        vm.cfdi_template = entity;
        vm.load = function (id) {
            Cfdi_template.get({id: id}, function(result) {
                vm.cfdi_template = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:cfdi_templateUpdate', function(event, result) {
            vm.cfdi_template = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
    }
})();
